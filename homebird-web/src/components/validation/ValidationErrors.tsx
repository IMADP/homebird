import {
    Alert,
    AlertDescription,
    AlertIcon,
    AlertTitle, Box, ListItem, UnorderedList
} from '@chakra-ui/react';

export interface ValidationError {
    error: string;
    path: Array<string>;
    attributes: Map<string, any>;
}

export interface ValidationErrorProps {
    errors: Array<ValidationError>;
}

const getKey = function (error: ValidationError): string {
    return error.path.join(".") + "." + error;
};

const getMessage = function (error: ValidationError): string {

    if (error.error === 'inUse') {
        return `The ${error.path[0]} is currently in use`;
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
                                {props.errors.map((e) =>
                                    <ListItem key={getKey(e)} ml="5">{getMessage(e)}</ListItem>
                                )}
                            </UnorderedList>
                        </AlertDescription>
                    </Alert>
                </Box>
            }
        </>
    );

}