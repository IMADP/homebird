import {
    Alert,
    AlertDescription,
    AlertIcon,
    AlertTitle, Box, ListItem, UnorderedList
} from '@chakra-ui/react';

export interface ValidationError {
    error: string;
    path: Array<string>;
    attributes?: Map<string, any>;
}

export interface ValidationErrorProps {
    errors: Array<ValidationError>;
}

const getMessage = function (error: ValidationError): string {

    if (error.error === 'inUse') {
        return `The ${error.path.slice(-1)[0]} is currently in use`;
    }

    if (error.error === 'notFound') {
        return `The ${error.path.slice(-1)[0]} was not found`;
    }

    if (error.error === 'notPassword') {
        return `The password is invalid`;
    }

    return error.error;
};

/**
 * RegisterPage
 * 
 * @returns 
 */
export const ValidationErrors = (props: ValidationErrorProps) => {
    return (
        <>
            {props.errors.length > 0 &&
                <Box mb="5">
                    <Alert status='error'>
                        <AlertIcon />
                        <AlertTitle>Please fix the following errors</AlertTitle>
                        <AlertDescription></AlertDescription>
                    </Alert>
                    <Alert status='warning'>
                        <AlertDescription>
                            <UnorderedList>
                                {props.errors.map((e, idx) =>
                                    <ListItem key={idx} ml="5">{getMessage(e)}</ListItem>
                                )}
                            </UnorderedList>
                        </AlertDescription>
                    </Alert>
                </Box>
            }
        </>
    );

}