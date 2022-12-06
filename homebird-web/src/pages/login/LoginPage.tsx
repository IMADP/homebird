import {
  Box,
  Button,
  Checkbox,
  Container,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Stack,
  Text,
  useBreakpointValue,
  useColorModeValue,
  useToast
} from '@chakra-ui/react';
import { formRequest } from 'api/api-client';
import { AuthApi } from 'api/auth-api';
import { Logo } from 'components/Logo';
import { ValidationError, ValidationErrors } from 'components/validation/ValidationErrors';
import { useState } from 'react';
import {
  Link,
  useLocation, useNavigate
} from "react-router-dom";
import { useAuthContext } from '../../auth/AuthContext';
import { PasswordField } from './PasswordField';

export const LoginPage = () => {
  let navigate = useNavigate();
  let location = useLocation();
  let auth = useAuthContext();
  const toast = useToast();
  const [errors, setErrors] = useState<Array<ValidationError>>([]);
  const [isLoading, setLoading] = useState<boolean>(false);

  let from = location.state?.from?.pathname || "/";

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    // retrieve form data
    const formData = new FormData(event.currentTarget);
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;

    // login
    await formRequest<string>({
      toast, setErrors, setLoading,
      onRequest: AuthApi.getToken({
        username: email,
        password: password,
        longExpire: false
      }),
      onSuccess: (token) => {
        auth.signin(email, token, () => { navigate(from, { replace: true }) });
      }
    });

  }

  return (
    <Container
      maxW="lg"
      py={{
        base: '12',
        md: '24',
      }}
      px={{
        base: '0',
        sm: '8',
      }}
    >
      <Stack spacing="8">
        <Stack spacing="6">
          <Logo height="20" />
          <Stack
            spacing={{
              base: '2',
              md: '3',
            }}
            textAlign="center"
          >
            <Heading
              size={useBreakpointValue({
                base: 'xs',
                md: 'sm',
              })}
            >
              Log in to your account
            </Heading>
            <HStack spacing="1" justify="center">
              <Text color="muted">Every bird needs a home</Text>
            </HStack>
          </Stack>
        </Stack>
        <Box
          py={{
            base: '0',
            sm: '8',
          }}
          px={{
            base: '4',
            sm: '10',
          }}
          bg={useBreakpointValue({
            base: 'transparent',
            sm: 'bg-surface',
          })}
          boxShadow={{
            base: 'none',
            sm: useColorModeValue('md', 'md-dark'),
          }}
          borderRadius={{
            base: 'none',
            sm: 'xl',
          }}
        >
          <form onSubmit={handleSubmit}>
            <ValidationErrors errors={errors} />

            <Stack spacing="6">
              <Stack spacing="5">
                <FormControl isRequired>
                  <FormLabel htmlFor="email">Email</FormLabel>
                  <Input id="email" name="email" type="email" required={false} />
                </FormControl>
                <PasswordField />
              </Stack>
              <HStack justify="space-between">
                <Checkbox defaultChecked>Remember me</Checkbox>
                <Button variant="link" colorScheme="blue" size="sm">
                  Forgot password?
                </Button>
              </HStack>
              <Stack spacing="6">
                <Button isLoading={isLoading} variant="primary" type='submit'>Sign in</Button>
              </Stack>
            </Stack>
          </form>
        </Box>
        <HStack justify="center" spacing="1">
          <Text fontSize="sm" color="muted">
            Don't have an account?
          </Text>
          <Link to="/register">
            <Button variant="link" colorScheme="blue" size="sm">
              Register
            </Button>
          </Link>
        </HStack>
      </Stack>
    </Container>)
}